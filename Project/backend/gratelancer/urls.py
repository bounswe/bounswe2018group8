# Created by Umut Barış Öztunç
from django.contrib import admin
from django.urls import path, include
from .views import CsrfView, PingView

# URL patterns for the URL dispatcher.
# Author: Umut Barış Öztunç
urlpatterns = [
    path('admin/', admin.site.urls),
    path('user/', include('user.urls')),
    path('ping/', PingView.as_view()),
    path('csrf/', CsrfView.as_view()),
]
