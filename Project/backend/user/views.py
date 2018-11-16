# Created by Umut Barış Öztunç
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import authentication, permissions
from rest_auth.views import LogoutView


# Extended the LogoutView from REST Auth module to make it only accessible by authenticated requests.
# Author: Umut Barış Öztunç
class LogoutViewEx(LogoutView):
    """
    Calls Django logout method and delete the Token object
    assigned to the current User object.
    Accepts/Returns nothing.
    """
    permission_classes = (permissions.IsAuthenticated,)
