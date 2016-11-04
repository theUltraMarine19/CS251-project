from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from .models import Course,Student, Importform, Feedback, Deadline, Question
from .forms import UserForm, CourseForm, ImportForm, FeedbackForm, DeadlineForm, QuestionForm
from datetime import date
import csv

from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from .Serializers import StudentSerializer
from rest_framework.decorators import api_view, permission_classes
from rest_framework import permissions



# Create your views here.


@api_view(['POST'])
@permission_classes((permissions.AllowAny,))
class Studentlist(APIView):
    class Meta:
        queryset = Student.objects.all()
        fields =['roll', 'password']
        allowed_methods = ['get','post']
        resource_name = 'Feeder06'

    def login(self, request, **kwargs):
        self.method_check(request, allowed=['post'])
        data = self.deserialize(request, request.raw_post_data,
                                format=request.META.get('CONTENT_TYPE', 'application/json'))

        username = data.get('username', '')
        password = data.get('password', '')

        user = authenticate(username=username, password=password)

        if user:
            if user.is_active:
                login(request, user)
                return self.create_response(request, {
                    'success': True
                })
            else:
                return self.create_response(request, {
                    'success': False,
                    'reason': 'disabled',
                }, HttpForbidden)
        else:
            return self.create_response(request, {
                'success': False,
                'reason': 'incorrect',
            }, HttpUnauthorized)




def index(request):
    if request.user.is_authenticated():
        course_list=Course.objects.all()
        context ={
            'course_list' : course_list,
        }
        return render(request,'Feeder06/UserHome.html', context)
    else:
        return render (request, 'Feeder06/Options.html',{})


@login_required
def adeadline(request):
    dregistered = False
    if request.method == 'POST':
        deadline_form = DeadlineForm(data=request.POST)
        if deadline_form.is_valid():
            deadline_list = Deadline.objects.all()
            for d in deadline_list:
                if d.name == deadline_form.data['name'] :
                    return HttpResponse('Deadline already exists. You deserve lame page')

            dregistered = True
            dead=  deadline_form.save()

            dead.save()


    else:
        deadline_form = DeadlineForm()

    return render(request, 'Feeder06/AddDeadline.html', {'deadline_form': deadline_form, 'registered': dregistered})




@login_required
def deadline(request):
    deadline_list = Deadline.objects.all()
    context ={
        'deadline_list' : deadline_list,
    }
    return render(request, 'Feeder06/Deadline.html', context)



@login_required
def details(request,course_id):
    course_list = Course.objects.all()
    context = {
        'course_list': course_list,
        'course_id' : course_id,
    }
    return render (request, 'Feeder06/Courses.html',context)


@login_required
def admindetails(request,course_id):
    course_list = Course.objects.all()
    context = {
        'course_list': course_list,
        'course_id' : course_id,
    }
    return render (request, 'Feeder06/AdminCourses.html',context)




def Alogin(request):
    if request.method == 'POST':

        username = request.POST['username']
        password = request.POST['password']

        user = authenticate(username=username, password=password)

        if user:
            if user.is_active:
                login(request, user)
                if (username == "admin" and password == "agents006"):
                    course_list = Course.objects.all()
                    context = {
                        'course_list': course_list,
                    }
                    return render(request, 'Feeder06/Adminhome.html', context)
                return HttpResponse('Invalid login details supplied.')
            else:
                return HttpResponse('Your account is disabled.')
        else:
            return HttpResponse('Invalid login details supplied.')
    else:
        if request.user.is_authenticated():
            course_list = Course.objects.all()
            context = {
                'course_list': course_list,
            }
            return render(request, 'Feeder06/Adminhome.html', context)
        else:
            return render(request, 'Feeder06/Adminform.html', {})

def Ilogin(request):
    if request.method == 'POST':

        username = request.POST['username']
        password = request.POST['password']

        user = authenticate(username=username, password=password)

        if user:
            if user.is_active:
                login(request, user)
                return HttpResponseRedirect('/Feeder06/')
            else:
                return HttpResponse('Your account is disabled.')
        else:
            print ('Invalid login details: {0}, {1}'.format(username, password))
            return HttpResponse('Invalid login details supplied.')

    else:
        return render(request, 'Feeder06/Instructorform.html', {})

def register(request):

    registered = False
    if request.method == 'POST':
        user_form = UserForm(data=request.POST)

        if user_form.is_valid() :
            # Save the user's form data to the database.
            user = user_form.save()
            user.set_password(user.password)
            user.save()

            registered = True

    else:
        user_form = UserForm()

    return render(request,'Feeder06/Register.html',{'user_form': user_form, 'registered': registered})

@login_required
def afeedback(request):
    feeddone=False
    if request.method == 'POST' and 'submit1' in request.POST:
            feedback_form = FeedbackForm(data=request.POST)
            if feedback_form.is_valid():
                course_list = Course.objects.all()

                flag=0
                for c in course_list:
                    if c.course_code == feedback_form.data['course_code']:
                        a=c
                        flag=1

                if flag == 0 :
                    return render(request,'Course does not exist',{})

                feed=Feedback(course = a, course_code=feedback_form.data['course_code'], name=feedback_form.data['name'], last_date=feedback_form.data['last_date'])
                feed.save()
                feeddone = True

    elif request.method == 'POST' and 'submit2' in request.POST:
            question_form = QuestionForm(data=request.POST)
            if question_form.is_valid():
                question = Question()
                question.form = Feedback.objects.latest('id')
                question.quest = question_form.data['quest']
                question.save()



                context={
                'feeddone' : True,
                'question_form': QuestionForm(),
                }


                return render(request,'Feeder06/Feedback.html',context)
    else:
            feedback_form = FeedbackForm()

    return render(request,'Feeder06/Feedback.html',{'feedback_form': feedback_form, 'feeddone': feeddone, 'question_form': QuestionForm()})



@login_required
def cregister(request):

    cregistered = False
    if request.method == 'POST':
        course_form = CourseForm(data=request.POST)

        if course_form.is_valid() :

            course_list = Course.objects.all()


            for c in course_list:
                if c.course_code == course_form.data['course_code']:
                    return HttpResponse('Course already exists. You deserve lame page')

            course = course_form.save()
            course.save()

            m = course

            d_endsem = Deadline()
            d_midsem = Deadline()

            d_endsem.course = m
            d_midsem.course = m

            d_endsem.name = "Endsem Exam"
            d_midsem.name = "Midsem Exam"

            d_endsem.deadline = m.endsem_date
            d_midsem.deadline = m.midsem_date

            if date.today() < m.endsem_date:
                d_endsem.running = True
            else:
                d_endsem.running = False

            if date.today() < m.midsem_date:
                d_midsem.running = True
            else:
                d_midsem.running = False

            d_endsem.save()
            d_midsem.save()

            f_midsem = Feedback()
            f_endsem = Feedback()

            f_endsem.name='Endsem Course feedback'
            f_midsem.name='Midsem Course feedback'

            f_endsem.last_date = m.endsem_date
            f_midsem.last_date = m.midsem_date

            f_midsem.course = m
            f_endsem.course = m

            f_endsem.save()
            f_midsem.save()

            q1 = Question()
            q2 = Question()
            q3 = Question()
            q4 = Question()
            q5 = Question()

            q1.quest = 'Did you find the course intellectually stimulating till now?'
            q2.quest = 'Was the instructor enthusiastic about teaching the course?'
            q3.quest = 'Do you think the course will be useful for your future?'
            q4.quest = 'Is the Instructor punctual?'
            q5.quest = 'Do you find the quizzes, assignments to be challenging enough?'

            q1.answer = ''
            q2.answer = ''
            q3.answer = ''
            q4.answer = ''
            q5.answer = ''

            q11 = Question()
            q12 = Question()
            q13 = Question()
            q14 = Question()
            q15 = Question()

            q11.quest = 'Did you find the course intellectually stimulating till now?'
            q12.quest = 'Was the instructor enthusiastic about teaching the course?'
            q13.quest = 'Do you think the course will be useful for your future?'
            q14.quest = 'Is the Instructor punctual?'
            q15.quest = 'Do you find the quizzes, assignments to be challenging enough?'

            q11.answer = ''
            q12.answer = ''
            q13.answer = ''
            q14.answer = ''
            q15.answer = ''

            q1.form = f_midsem
            q2.form = f_midsem
            q3.form = f_midsem
            q4.form = f_midsem
            q5.form = f_midsem

            q11.form = f_endsem
            q12.form = f_endsem
            q13.form = f_endsem
            q14.form = f_endsem
            q15.form = f_endsem


            q1.save()
            q2.save()
            q3.save()
            q4.save()
            q5.save()

            q11.save()
            q12.save()
            q13.save()
            q14.save()
            q15.save()

            cregistered = True

    else:
        course_form = CourseForm()

    return render(request,'Feeder06/CRegister.html',{'course_form': course_form, 'registered': cregistered})

@login_required
def simport(request):
    if request.method == 'POST':
        course_list = Course.objects.all()
        course_code=request.POST['course_code']
        path_to_file=request.POST['path_to_file']
        for c in course_list:
            if c.course_code==course_code:
                a=c
        with open(path_to_file) as f:
            reader = csv.reader(f)
            for row in reader:
                student=Student(name=row[0], roll=row[1], password=row[2],course=a)
                student.save()
        if request.user.is_authenticated():
            course_list = Course.objects.all()
            context = {
                'course_list': course_list,
            }
            return render(request, 'Feeder06/Adminhome.html', context)
        else:
            return render(request, 'Feeder06/Adminform.html', {})
    else:
        import_form=ImportForm()
        return render(request,'Feeder06/ImportForm.html',{'import_form': import_form})




def Ilogout(request):
    logout(request)

    return HttpResponseRedirect('/Feeder06/')
