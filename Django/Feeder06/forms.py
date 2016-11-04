from django import forms
from django.contrib.auth.models import User
from Feeder06.models import Course, Student, Importform, Deadline, Feedback, Question


class UserForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = User
        fields = ('username', 'password')

class StudentForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = User
        fields = ('username', 'password')

class CourseForm(forms.ModelForm):

    class Meta:
        model = Course
        fields = ('course_code', 'course_name', 'instructor_name','course_year','midsem_date','endsem_date')

class ImportForm(forms.ModelForm):

    class Meta:
        model = Importform
        fields=('course_code', 'path_to_file')

class FeedbackForm(forms.ModelForm):

    class Meta:
        model = Feedback
        fields = ('course_code', 'name', 'last_date')

class QuestionForm(forms.ModelForm):

    class Meta:
        model = Question
        fields = ('quest',)

class DeadlineForm(forms.ModelForm):

    class Meta:
        model = Deadline
        fields = ('course', 'name', 'deadline', 'running')


