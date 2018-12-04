# Created by Ozan Kınasakal
from django.urls import path
from .views import ProjectListView, ProjectManageView, ProjectCreateView, ProjectListManageView, ProjectSearchView

urlpatterns = [
    path('<int:pk>/', ProjectManageView.as_view()),
    path('', ProjectListManageView.as_view()),
    path('search/<str:keyword>', ProjectSearchView.as_view(), name='project_search'),
]
