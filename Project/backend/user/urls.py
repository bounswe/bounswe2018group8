# Created by Umut Barış Öztunç
from django.contrib import admin
from django.urls import path, include, re_path
from rest_auth.views import LoginView, UserDetailsView, PasswordChangeView, PasswordResetView, PasswordResetConfirmView
from .views import LogoutViewEx, ProjectListView, ProjectManageView, ProjectCreateView

# URL patterns of the URL dispatcher of User app.
# Author: Umut Barış Öztunç, Ozan Kınasakal
urlpatterns = [
    path('login/', LoginView.as_view(), name='login'),
    path('logout/', LogoutViewEx.as_view(), name='logout'),
    path('password/change/', PasswordChangeView.as_view(), name='password_change'),
    path('password/reset/', PasswordResetView.as_view(), name='password_reset'),
    path('password/reset/confirm/', PasswordResetConfirmView.as_view(), name='password_reset_confirm'),
    path('current/', UserDetailsView.as_view(), name='current_user'),
    path('register/', include('rest_auth.registration.urls')),
    path('projects/',ProjectListView.as_view()),
    path('projects/<int:pk>/', ProjectManageView.as_view()),
    path('projects/create/', ProjectCreateView.as_view()),
]
