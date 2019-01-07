# Created by Ozan Kınasakal
from django.test import TestCase
from .models import User,Project
from datetime import datetime
from datetime import timedelta

# Author: Ozan Kınasakal
class ProjectModelTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        # Set up non-modified objects used by all test methods
        User.objects.create_user(username='bigbob', password='securepass', first_name='Big', last_name='Bob')
        Project.objects.create(title='software project', description='project description', deadline=datetime.now() + timedelta(days = 30), min_price='100', max_price='200', freelancer=None, client =User.objects.get(id=1),status='active')

    def setUp(self):
        self.project = Project.objects.get(id=1)

    def test_client_label(self):
        field_label = self.project._meta.get_field('client').verbose_name
        self.assertEquals(field_label, 'client')

    def test_freelancer_label(self):
        field_label = self.project._meta.get_field('freelancer').verbose_name
        self.assertEquals(field_label, 'freelancer')

    def test_title_label(self):
        field_label = self.project._meta.get_field('title').verbose_name
        self.assertEquals(field_label, 'title')

    def test_description_label(self):
        field_label = self.project._meta.get_field('description').verbose_name
        self.assertEquals(field_label, 'description')

    def test_deadline_label(self):
        field_label = self.project._meta.get_field('deadline').verbose_name
        self.assertEquals(field_label, 'deadline')

    def test_max_price_label(self):
        field_label = self.project._meta.get_field('max_price').verbose_name
        self.assertEquals(field_label, 'max price')

    def test_status_label(self):
        field_label = self.project._meta.get_field('status').verbose_name
        self.assertEquals(field_label, 'status')

    def test_average_bid_label(self):
        field_label = self.project._meta.get_field('average_bid').verbose_name
        self.assertEquals(field_label, 'average bid')

    def test_bid_count_label(self):
        field_label = self.project._meta.get_field('bid_count').verbose_name
        self.assertEquals(field_label, 'bid count')

    def test_image_label(self):
        field_label = self.project._meta.get_field('image').verbose_name
        self.assertEquals(field_label, 'image')

    def test_title_max_length(self):
        max_length = self.project._meta.get_field('title').max_length
        self.assertEquals(max_length, 100)

    def test_status_max_length(self):
        status = self.project

    def test_default_image(self):
        image = self.project.image
        self.assertEquals(image.name, 'images/projects/no-image.jpg')

    def test_default_average_bid(self):
        average_bid = self.project.average_bid
        self.assertEquals(average_bid, 0.0)

    def test_default_bid_count(self):
        bid_count = self.project.bid_count
        self.assertEquals(bid_count, 0)
