# Created by Umut Barış Öztunç
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import authentication, permissions
from rest_auth.views import LogoutView

# A test view accessible by authenticated users who are authenticated with Token Authentication.
# Welcomes the user with his/her name.
# Author: Umut Barış Öztunç
class TestAuthView(APIView):
    authentication_classes = (authentication.TokenAuthentication,)
    permission_classes = (permissions.IsAuthenticated,)

    def get(self, request, format=None):
        return Response("Hello {0}!".format(request.user))


# Extended the LogoutView from REST Auth module to make it accept only Token Authenticated requests.
# Author: Umut Barış Öztunç
class LogoutViewEx(LogoutView):
    authentication_classes = (authentication.TokenAuthentication,)
