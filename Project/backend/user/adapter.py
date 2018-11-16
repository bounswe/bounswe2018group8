import os
from allauth.account.adapter import DefaultAccountAdapter
from django.conf import settings
from rest_framework.response import Response

class UserAccountAdapter(DefaultAccountAdapter):

    def save_user(self, request, user, form, commit=True):
        super(UserAccountAdapter, self).save_user(request, user, form, commit=False)
        user.is_client = form.cleaned_data['is_client']
        user.save()
        return user
        

    def respond_email_verification_sent(self, request, user):
        pass
        
        
    def get_email_confirmation_url(self, request, emailconfirmation):
        url = os.path.join(settings.URL_FRONT, 'user/activate/' + emailconfirmation.key)
        return url
        

