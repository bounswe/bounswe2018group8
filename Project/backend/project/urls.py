# Created by Ozan Kınasakal
from django.urls import path
from .views import ProjectListView, ProjectManageView, ProjectCreateView, ProjectListManageView

urlpatterns = [
    path('<int:pk>/', ProjectManageView.as_view()),
    path('', ProjectListManageView.as_view()),
]
