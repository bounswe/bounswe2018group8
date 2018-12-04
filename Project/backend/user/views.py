# Created by Umut Barış Öztunç
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_auth.views import LogoutView
from rest_framework.generics import ListAPIView, RetrieveAPIView
from .models import User
from .serializers import UserSerializer

# Extended the LogoutView from REST Auth module to make it only accessible by authenticated requests.
# Added the custom ProjectViews using APIView's
# Author: Umut Barış Öztunç
class LogoutViewEx(LogoutView):
    """
    Calls Django logout method and delete the Token object
    assigned to the current User object.
    Accepts/Returns nothing.
    """
    permission_classes = (IsAuthenticated,)



class UserRetrieveView(RetrieveAPIView):
    permission_classes = (AllowAny,)
    queryset = User.objects.all()
    serializer_class = UserSerializer
    
    
class UserListView(ListAPIView):
    permission_classes = (AllowAny,)
    queryset = User.objects.all()
    serializer_class = UserSerializer

        
