# Created by Umut Barış Öztunç
from django.contrib import admin
from rest_auth.views import UserDetailsView
from django.urls import path, include, re_path
from .views import LogoutViewEx, UserRetrieveView, UserListView, UserSearchView, UserDetailsViewEx

# URL patterns of the URL dispatcher of User app.
urlpatterns = [
    path('self/', UserDetailsViewEx.as_view(), name='user_self'),
    path('<int:pk>/', UserRetrieveView.as_view(), name='user_retrieve'),
    path('', UserListView.as_view(), name='user_list'),
    path('search/<str:name>/', UserSearchView.as_view(), name='user_search'),
]
