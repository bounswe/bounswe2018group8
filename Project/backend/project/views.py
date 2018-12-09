#Author: Ozan Kınasakal
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import authentication, permissions
from rest_framework import generics
from .models import Project
from .serializers import ProjectSerializer, BidSerializer
from django.db.models import Q
from user.permissions import IsClient, IsFreelancer

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
    permission_classes = (permissions.IsAuthenticated, IsClient,)

class ProjectDestroyView(generics.DestroyAPIView):
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated, IsClient,)

class ProjectCreateBidView(generics.CreateAPIView):
    serializer_class = BidSerializer
    permission_classes = (permissions.IsAuthenticated, IsFreelancer,)

    def get_serializer_context(self):
        context = super(ProjectCreateBidView, self).get_serializer_context()
        context['id'] = self.kwargs['pk']
        return context

    # def perform_create(self,serializer):
    #     serializer.save(project_id= self.kwargs.get('pk'))
    #     serializer.save(freelancer_id= self.request.user.freelancer)


class ProjectManageView(BaseManageView):
    VIEWS_BY_METHOD = {
        'DELETE': ProjectDestroyView.as_view,
        'GET': ProjectDetailsView.as_view,
        'PUT': ProjectUpdateView.as_view,
        'PATCH': ProjectUpdateView.as_view,
        'POST' : ProjectCreateBidView.as_view
    }



class ProjectCreateView(generics.CreateAPIView):
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated, IsClient,)


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
        return Project.objects.filter(client__user__id=user_id)


# Author: Umut Baris Oztunc
class SelfProjectsView(generics.ListAPIView):
    """
    List projects of the authenticated user.
    """
    serializer_class = ProjectSerializer
    permission_classes = (permissions.IsAuthenticated,)

    def get_queryset(self):
        return Project.objects.filter(client__user=self.request.user)
