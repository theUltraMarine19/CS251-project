from django.contrib import admin
from .models import Course, Student, Feedback, Question, Deadline
# Register your models here.

admin.site.register(Course)
admin.site.register(Student)
admin.site.register(Feedback)
admin.site.register(Question)
admin.site.register(Deadline)

