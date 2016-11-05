from rest_framework import serializers
from .models import Student, Deadline, Feedback

class StudentSerializer(serializers.ModelSerializer):

    class Meta :
        model = Student
        fields = ['roll', 'password']

class DeadlineSerializer(serializers.ModelSerializer):

    class Meta:
        model = Deadline
        fields= ' __all__ '