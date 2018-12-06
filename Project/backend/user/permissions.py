# Created By Umut Baris Oztunc
from rest_framework.permissions import BasePermission

class IsClient(BasePermission):
    """
    Allows access only to clients.
    """

    def has_permission(self, request, view):
        return bool(request.user and request.user.is_client)
