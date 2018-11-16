# Created by Umut Barış Öztunç
from django.contrib import admin
from django.urls import path, include

# URL patterns for the URL dispatcher.
# Author: Umut Barış Öztunç
urlpatterns = [
    path('admin/', admin.site.urls),
    path('user/', include('user.urls')),
]
