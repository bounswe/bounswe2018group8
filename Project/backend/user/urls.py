# Created by Umut Barış Öztunç
from django.contrib import admin
from django.urls import path, include
from rest_auth.views import LoginView
from .views import TestAuthView, LogoutViewEx

# URL patterns of the URL dispatcher of User app.
# Author: Umut Barış Öztunç
urlpatterns = [
    path('test_auth/', TestAuthView.as_view(), name='test_auth'),
    path('login/', LoginView.as_view(), name='login'),
    path('logout/', LogoutViewEx.as_view(), name='logout'),
    path('register/', include('rest_auth.registration.urls')),
]
