# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-11-01 20:04
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Feeder06', '0005_student_password'),
    ]

    operations = [
        migrations.CreateModel(
            name='Importform',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('course_code', models.CharField(max_length=10)),
                ('path_to_file', models.CharField(max_length=500)),
            ],
        ),
    ]
