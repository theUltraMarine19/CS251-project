from django.db import models
from django.contrib.auth.models import User


# Create your models here.
class Course(models.Model):
    course_name = models.CharField(max_length=256)
    course_code = models.CharField(max_length=10)
    instructor_name = models.CharField(max_length=100)
    course_year= models.CharField(max_length=100)
    midsem_date = models.DateField(max_length=100)
    endsem_date = models.DateField(max_length=100)
    def __str__(self):
        return self.course_code + '-' + self.course_name




class Student(models.Model):
    course = models.ForeignKey(Course, on_delete=models.CASCADE)
    name = models.CharField(max_length=512)
    roll = models.CharField(max_length=50)
    password = models.CharField(max_length=100)

    # date = models.DateField(auto_now=False,auto_now_add=False)
    def __str__(self):
        return self.roll

class Importform(models.Model):
    course_code = models.CharField(max_length=10)
    path_to_file = models.CharField(max_length=500)

class Deadline(models.Model):
    course = models.ForeignKey(Course, on_delete=models.CASCADE)
    name = models.CharField(max_length=512)
    deadline = models.DateField(max_length=50)
    running = models.BooleanField(default = True)

    def __str__(self):
        return self.course.course_code + '-' + self.name

class Feedback(models.Model):
    course = models.ForeignKey(Course, on_delete=models.CASCADE)
    course_code = models.CharField(max_length=10)
    name = models.CharField(max_length=256)
    last_date = models.DateField()

    def __str__(self):
        return self.course.course_code + '-' + self.name

class Question(models.Model):
    form = models.ForeignKey(Feedback, on_delete=models.CASCADE)
    quest = models.CharField(max_length=256)
    answer = models.CharField(max_length =256)

    def __str__(self):
        return self.quest


