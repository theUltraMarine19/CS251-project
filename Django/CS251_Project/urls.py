from django.conf.urls import include, url
from django.contrib import admin

admin.autodiscover()
#from .views import AuthComplete, LoginError

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url('', include('social.apps.django_app.urls', namespace='social')),
    url('', include('django.contrib.auth.urls', namespace='auth')),
    #url(r'^complete/(?P<backend>[^/]+)/$', AuthComplete.as_view()),
    url(r'^Feeder06/', include('Feeder06.urls')),
]
