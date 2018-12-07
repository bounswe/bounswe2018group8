# Created by Umut Barış Öztunç
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_auth.views import LogoutView
from rest_framework.generics import ListAPIView, RetrieveAPIView
from .models import User
from .serializers import UserSerializer
from django.db.models import Q


# Extended the LogoutView from REST Auth module to make it only accessible by authenticated requests.
# Author: Umut Barış Öztunç
class LogoutViewEx(LogoutView):
    """
    Calls Django logout method and delete the Token object
    assigned to the current User object.
    Accepts/Returns nothing.
    """
    permission_classes = (IsAuthenticated,)


# Author: Umut Baris Oztunc
class UserRetrieveView(RetrieveAPIView):
    """
    Retrieve specific users by id.
    """
    permission_classes = (AllowAny,)
    queryset = User.objects.all()
    serializer_class = UserSerializer
    
    
# Author: Umut Baris Oztunc
class UserListView(ListAPIView):
    """
    List all users.
    """
    permission_classes = (AllowAny,)
    queryset = User.objects.all()
    serializer_class = UserSerializer


# Author: Umut Baris Oztunc
class UserSearchView(ListAPIView):
    """
    Search users by name.
    """
    permision_classes = (AllowAny,)
    serializer_class = UserSerializer
    
    def get_queryset(self):
        name = self.kwargs['name']
        return User.objects.filter(Q(first_name__icontains=name) | Q(last_name__icontains=name))
