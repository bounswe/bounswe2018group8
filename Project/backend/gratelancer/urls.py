# Created by Umut Barış Öztunç
from django.contrib import admin
from django.urls import path, include
from user.views import HomepageView

# URL patterns for the URL dispatcher.
# Author: Umut Barış Öztunç, Ozan Kınasakal
urlpatterns = [
    path('admin/', admin.site.urls),
    path('user/', include('user.urls')),
    path('', HomepageView.as_view()),
]
