from django.conf.urls import url
from . import views
from django.contrib import admin

app_name = 'Feeder06'


urlpatterns = [
    url(r'^$', views.index,name='index'),
    url(r'^(?P<course_id>[A-Z]{2}[_][0-9]{3})/$', views.details,name='details'),
    url(r'^admin/(?P<course_id>[A-Z]{2}[_][0-9]{3})/$', views.admindetails,name='admindetails'),
    url(r'^admin/', admin.site.urls, name='admin'),
    url(r'^adminlogin/', views.Alogin, name='adminlogin'),
    url(r'^deadline/', views.deadline, name='deadline'),
    url(r'^adeadline/', views.adeadline, name='adeadline'),
    url(r'^feedback/', views.afeedback, name='feedback'),
    url(r'^simport/', views.simport, name='simport'),
    url(r'^login/', views.Ilogin, name='login'),
    url(r'^register/', views.register, name='register'),
    url(r'^cregister/', views.cregister, name='cregister'),
	url(r'^logout/', views.Ilogout, name='logout'),
    url(r'^slogin/', views.Studentlist, name='slogin'),
    url(r'^social/', views.social, name='social'),
]

'''def get_admin_urls(urls):
    def get_urls():
        my_urls = [
            (, admin.site.admin_view()),
        ]
        return my_urls + urls
    return get_urls

admin_urls = get_admin_urls(admin.site.get_urls())
admin.site.get_urls = admin_urls'''
