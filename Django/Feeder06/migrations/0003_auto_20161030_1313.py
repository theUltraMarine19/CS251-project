# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-30 13:13
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Feeder06', '0002_course_course_y'),
    ]

    operations = [
        migrations.RenameField(
            model_name='course',
            old_name='course_y',
            new_name='course_year',
        ),
    ]