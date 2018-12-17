#Author: Ozan Kınasakal
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import authentication, permissions
from rest_framework import generics
from .models import Project, Bid
from .serializers import ProjectSerializer, BidSerializer
from django.db.models import Q
from user.permissions import IsClient, IsFreelancer
from django.utils.translation import ugettext_lazy as _
from rest_framework import status

class BaseManageView(APIView):
    """
    The base class for ManageViews
        A ManageView is a view which is used to dispatch the requests to the appropriate views
        This is done so that we can use one URL with different methods (GET, PUT, etc)
    """
    def dispatch(self, request, *args, **kwargs):
        if not hasattr(self, 'VIEWS_BY_METHOD'):
            raise Exception('VIEWS_BY_METHOD static dictionary variable must be defined on a ManageView class!')
        if request.method in self.VIEWS_BY_METHOD:
            return self.VIEWS_BY_METHOD[request.method]()(request, *args, **kwargs)

        return Response(status=405)


class ProjectDetailsView(generics.RetrieveAPIView):
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer
    permission_classes = (permissions.AllowAny,)

class ProjectUpdateView(generics.UpdateAPIView):
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated,)

class ProjectDestroyView(generics.DestroyAPIView):
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated,)


class ProjectManageView(BaseManageView):
    VIEWS_BY_METHOD = {
        'DELETE': ProjectDestroyView.as_view,
        'GET': ProjectDetailsView.as_view,
        'PUT': ProjectUpdateView.as_view,
        'PATCH': ProjectUpdateView.as_view,
    }



class ProjectCreateView(generics.CreateAPIView):
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated,)


class ProjectListView(generics.ListAPIView):
    serializer_class = ProjectSerializer
    queryset = Project.objects.all().order_by('-id')
    permission_classes = (permissions.AllowAny,)

class ProjectListManageView(BaseManageView):
    VIEWS_BY_METHOD = {
        'POST': ProjectCreateView.as_view,
        'GET' : ProjectListView.as_view
    }


# Author: Umut Baris Oztunc
class ProjectSearchView(generics.ListAPIView):
    """
    Search projects by a keyword.
    """
    serializer_class = ProjectSerializer
    permission_classes = (permissions.AllowAny,)

    def get_queryset(self):
        keyword = self.kwargs['keyword']
        return Project.objects.filter(Q(description__icontains=keyword) | Q(title__icontains=keyword))


# Author: Umut Baris Oztunc
class UserProjectsView(generics.ListAPIView):
    """
    List projects of a specific user.
    """
    serializer_class = ProjectSerializer
    permission_classes = (permissions.AllowAny,)

    def get_queryset(self):
        user_id = self.kwargs['id']
        return Project.objects.filter(user__id=user_id)


# Author: Umut Baris Oztunc
class SelfProjectsView(generics.ListAPIView):
    """
    List projects of the authenticated user.
    """
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated,)

    def get_queryset(self):
        return Project.objects.filter(client__user=self.request.user)


# Author: Umut Baris Oztunc
class MakeBidView(APIView):
    """
    Make bid to a project.
    """
    permission_classes = (permissions.IsAuthenticated,)
    
    def post(self, request, id, format=None):
        amount = request.data.get('amount')
        if amount:
            try:
                amount = float(amount)
            except:
                return Response({"amount": _("Must be a positive number.")}, status.HTTP_400_BAD_REQUEST)
            if amount > 0.0:
                try:
                    bid = Bid.objects.get(
                        freelancer = request.user,
                        project__id = id
                    )
                    bid.amount = amount
                    bid.save()
                    return Response({"detail": _("Bid has successfully been updated.")}, status.HTTP_200_OK)
                except Bid.DoesNotExist:
                    bid = Bid.objects.create(
                        freelancer = request.user,
                        amount = amount,
                        project = Project.objects.get(id=id)
                    )
                    bid.save()
                    return Response({"detail": _("Bid has successfully been made.")}, status.HTTP_201_CREATED)
            else:
                return Response({"amount": _("Must be a positive number.")}, status.HTTP_400_BAD_REQUEST)
        else:
            return Response({"amount": _("This field is required.")}, status.HTTP_400_BAD_REQUEST)

