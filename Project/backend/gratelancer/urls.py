# Created by Umut Barış Öztunç
from django.contrib import admin
from django.urls import path, include
from user.views import LogoutViewEx
from rest_auth.views import LoginView, UserDetailsView, PasswordChangeView, PasswordResetView, PasswordResetConfirmView
from django.conf.urls.static import static
from django.conf import settings

# URL patterns for the URL dispatcher.
# Author: Umut Barış Öztunç, Ozan Kınasakal
urlpatterns = [
    path('admin/', admin.site.urls),
    path('login/', LoginView.as_view(), name='login'),
    path('logout/', LogoutViewEx.as_view(), name='logout'),
    path('password/change/', PasswordChangeView.as_view(), name='password_change'),
    path('password/reset/', PasswordResetView.as_view(), name='password_reset'),
    path('password/reset/confirm/', PasswordResetConfirmView.as_view(), name='password_reset_confirm'),
    path('register/', include('rest_auth.registration.urls')),
    path('users/', include('user.urls')),
    path('projects/', include('project.urls')),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
