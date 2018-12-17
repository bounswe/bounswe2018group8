# Created by Ozan Kınasakal
from django.urls import path
from .views import ProjectManageView, ProjectListManageView, ProjectSearchView, UserProjectsView, SelfProjectsView, MakeBidView

urlpatterns = [
    path('<int:pk>/', ProjectManageView.as_view()),
    path('', ProjectListManageView.as_view()),
    path('search/<str:keyword>/', ProjectSearchView.as_view(), name='project_search'),
    path('user/<int:id>/', UserProjectsView.as_view(), name='user_projects'),
    path('user/self/', SelfProjectsView.as_view(), name='self_projects'),
    path('<int:id>/bid/', MakeBidView.as_view(), name='make_bid'),
]
