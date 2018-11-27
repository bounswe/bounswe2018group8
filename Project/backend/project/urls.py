# Created by Ozan Kınasakal
from django.urls import path
from .views import ProjectListView, ProjectManageView, ProjectCreateView

urlpatterns = [
    path('<int:pk>/', ProjectManageView.as_view()),
]
