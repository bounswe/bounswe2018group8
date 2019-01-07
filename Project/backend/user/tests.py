# Created by Umut Baris Oztunc
from django.test import TestCase
from .models import User, Skill
from rest_framework.test import APITestCase, APIRequestFactory, APIClient, force_authenticate
from rest_framework import status
from .views import LogoutViewEx, UserRetrieveView, UserListView, UserSearchView, UserDetailsViewEx, DepositView

# Author: Umut Baris Oztunc
class UserModelTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        # Set up non-modified objects used by all test methods
        User.objects.create_user(username='bigbob', password='securepass', first_name='Big', last_name='Bob')
        
    def setUp(self):
        self.user = User.objects.get(id=1)

    def test_first_name_label(self):
        field_label = self.user._meta.get_field('first_name').verbose_name
        self.assertEquals(field_label, 'first name')

    def test_last_name_label(self):
        field_label = self.user._meta.get_field('last_name').verbose_name
        self.assertEquals(field_label, 'last name')
        
    def test_bio_label(self):
        field_label = self.user._meta.get_field('bio').verbose_name
        self.assertEquals(field_label, 'bio')
        
    def test_skills_label(self):
        field_label = self.user._meta.get_field('skills').verbose_name
        self.assertEquals(field_label, 'skills')
        
    def test_balance_label(self):
        field_label = self.user._meta.get_field('balance').verbose_name
        self.assertEquals(field_label, 'balance')
        
    def test_avatar_label(self):
        field_label = self.user._meta.get_field('avatar').verbose_name
        self.assertEquals(field_label, 'avatar')

    def test_bio_max_length(self):
        max_length = self.user._meta.get_field('bio').max_length
        self.assertEquals(max_length, 1000)
        
    def test_default_avatar(self):
        avatar = self.user.avatar
        self.assertEquals(avatar.name, 'images/users/no-avatar.jpg')
        
    def test_default_balance(self):
        balance = self.user.balance
        self.assertEquals(balance, 0.0)


# Author: Umut Baris Oztunc
class SkillModelTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        # Set up non-modified objects used by all test methods
        Skill.objects.create(name='malware analysis')
        
    def setUp(self):
        self.skill = Skill.objects.get(id=1)
        
    def test_object_name_is_skill_name(self):
        self.assertEquals(self.skill.name, str(self.skill))


# Author: Umut Baris Oztunc        
class UserViewTest(APITestCase):

    def setUp(self):
        self.user = User.objects.create_user(
            username = 'bigbob',
            password = 'securepass',
            email = 'bigbob@example.com',
            first_name = 'Bob',
            last_name = 'Marley'
        )
        
    def test_register_user(self):
        """
        Ensure we can create a new account object.
        """
        url = '/register/'
        data = {
            'username': 'alicecooper',
            'password1': 'strongpass',
            'password2': 'strongpass',
            'email': 'me@alicecooper.com',
            'first_name': 'Alice',
            'last_name': 'Cooper',        
        }
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(User.objects.get(username='alicecooper').email, 'me@alicecooper.com')
        self.assertEqual(User.objects.get(username='alicecooper').first_name, 'Alice')
        self.assertEqual(User.objects.get(username='alicecooper').last_name, 'Cooper')
        
    def test_user_login(self):
        """
        Ensure we can login with valid user credentials only.
        """
        url = '/login/'
        data = {
            'username': 'bigbob',
            'password': 'securepass',
        }
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        data['password'] = 'wrongpass'
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        
    def test_guests_cannot_see_user_details(self):
        """
        Ensure guests cannot access user details endpoint.
        """
        url = '/users/self/'
        response = self.client.post(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        
    def test_users_can_see_user_details(self):
        """
        Ensure authenticated users can access user details endpoint.
        """
        url = '/users/self/'
        view = UserDetailsViewEx.as_view()
        factory = APIRequestFactory()
        request = factory.get(url)
        force_authenticate(request, user=self.user)
        response = view(request)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        
    def test_users_can_deposit(self):
        """
        Ensure authenticated users can deposit money into their accounts.
        """
        url = '/users/self/deposit/'
        data = {
            'amount': 100.0,
        }
        view = DepositView.as_view()
        factory = APIRequestFactory()
        request = factory.post(url, data)
        force_authenticate(request, user=self.user)
        response = view(request, data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(self.user.balance, 100.0)
        
    def test_guests_cannot_deposit(self):
        """
        Ensure guests cannot deposit money.
        """
        url = '/users/self/deposit/'
        data = {
            'amount': 100.0,
        }
        response = self.client.post(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        
    def test_users_can_update_skills(self):
        """
        Ensure users can update their skills in their profile details.
        """
        url = '/users/self/'
        data = {
            'skills': [
                'reverse engineering',
                'binary analysis',
                'cryptography',
                'binary exploitation',
            ],
        }
        self.client.login(username='bigbob', password='securepass')
        response = self.client.patch(url, data, format='json')
        self.client.logout()
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(self.user.skills.all()[0].name, 'reverse engineering')
        self.assertEqual(self.user.skills.all()[1].name, 'binary analysis')
        self.assertEqual(self.user.skills.all()[2].name, 'cryptography')
        self.assertEqual(self.user.skills.all()[3].name, 'binary exploitation')
        
    def test_user_search(self):
        """
        Ensure users can be searched by both first name and last name.
        """
        response = self.client.get('/users/search/bob/')
        self.assertEqual(response.data[0]['first_name'], 'Bob')
        response = self.client.get('/users/search/marley/')
        self.assertEqual(response.data[0]['last_name'], 'Marley')
