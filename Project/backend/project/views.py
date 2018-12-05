#Author: Ozan Kınasakal
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import authentication, permissions
from rest_framework import generics
from .models import Project
from .serializers import ProjectSerializer, ProjectCreateSerializer
from django.contrib.auth.mixins import UserPassesTestMixin,LoginRequiredMixin
from django.db.models import Q

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

class ProjectUpdateView(LoginRequiredMixin,UserPassesTestMixin,generics.UpdateAPIView):
    raise_exception = True
    def test_func(self):
        return (self.get_object().client_id == self.request.user.pk)
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer

class ProjectDestroyView(UserPassesTestMixin,generics.DestroyAPIView):
    raise_exception = True
    def test_func(self):
        return (self.get_object().client_id == self.request.user.pk)
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer


class ProjectManageView(BaseManageView):
    VIEWS_BY_METHOD = {
        'DELETE': ProjectDestroyView.as_view,
        'GET': ProjectDetailsView.as_view,
        'PUT': ProjectUpdateView.as_view,
        'PATCH': ProjectUpdateView.as_view
    }



class ProjectCreateView(LoginRequiredMixin,UserPassesTestMixin, generics.CreateAPIView):
    raise_exception = True
    def test_func(self):
        return self.request.user.is_client

    serializer_class = ProjectCreateSerializer

    def perform_create(self, serializer):
        serializer.save(client=self.request.user.client)
        serializer.save(status='active')

class MyProjectsView(generics.ListAPIView):
    serializer_class = ProjectSerializer
    def get_queryset(self):
        user = self.request.user
        return Project.objects.filter(client_id = user.pk)

class ProjectListView(generics.ListAPIView):
    serializer_class = ProjectSerializer
    queryset = Project.objects.all().order_by('-id')
    permission_classes = (permissions.AllowAny,)

class ProjectListManageView(BaseManageView):
    VIEWS_BY_METHOD = {
        'POST': ProjectCreateView.as_view,
        'GET' : ProjectListView.as_view
    }


# Search projects by a keyword
# Author: Umut Baris Oztunc
class ProjectSearchView(generics.ListAPIView):
    serializer_class = ProjectSerializer
    permission_classes = (permissions.AllowAny,)

    def get_queryset(self):
        keyword = self.kwargs['keyword']
        return Project.objects.filter(Q(description__icontains=keyword) | Q(title__icontains=keyword))
