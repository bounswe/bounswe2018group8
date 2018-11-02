# Created by Umut Barış Öztunç
from rest_framework.views import APIView
from rest_framework.response import Response
from django.middleware.csrf import get_token

# Returns a valid CSRF token
# Author: Umut Barış Öztunç
class CsrfView(APIView):
    def post(self, request, format=None):
        return Response({'csrfToken': get_token(request)})


# Checks whether the server is up and returns a packet if the server is up.
# Author: Umut Barış Öztunç
class PingView(APIView):
    def post(self, request, format=None):
        return Response({'result': 'OK'})
