from django.contrib.admin.sites import AdminSite, site

def get_urls(self):
    from django.conf.urls import patterns, url, include

    if settings.DEBUG:
        self.check_dependencies()

    def wrap(view, cacheable=False):
        def wrapper(*args, **kwargs):
            return self.admin_view(view, cacheable)(*args, **kwargs)

        return update_wrapper(wrapper, view)

    # Admin-site-wide views.
    urlpatterns = patterns('',

                           url(r'^$',
                               wrap(self.index),
                               name='index'),
                           url(r'^logout/$',
                               wrap(self.logout),
                               name='logout'),
                           url(r'^password_change/$',
                               wrap(self.password_change, cacheable=True),
                               name='password_change'),
                           url(r'^password_change/done/$',
                               wrap(self.password_change_done, cacheable=True),
                               name='password_change_done'),
                           url(r'^jsi18n/$',
                               wrap(self.i18n_javascript, cacheable=True),
                               name='jsi18n'),
                           url(r'^r/(?P\d+)/(?P.+)/$',
                               wrap(contenttype_views.shortcut),
                               name='view_on_site'),
                           url(r'^(?P\w+)/$',
                               wrap(self.app_index),
                               name='app_list')
                           )

    # Add in each model's views.
    for model, model_admin in six.iteritems(self._registry):
        urlpatterns += patterns('',
                                url(r'^%s/%s/' % (model._meta.app_label, model._meta.module_name),
                                    include(model_admin.urls))
                                )
    return urlpatterns


@property
def urls(self):
    return self.get_urls(), self.app_name, self.name