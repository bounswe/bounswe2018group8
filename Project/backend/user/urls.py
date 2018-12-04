# Created by Umut Barış Öztunç
from django.contrib import admin
from rest_auth.views import UserDetailsView
from django.urls import path, include, re_path
from .views import LogoutViewEx, UserRetrieveView, UserListView

# URL patterns of the URL dispatcher of User app.
urlpatterns = [
    path('self/', UserDetailsView.as_view(), name='user_self'),
    path('<int:pk>/', UserRetrieveView.as_view(), name='user_retrieve'),
    path('', UserListView.as_view(), name='user_list'),
]
