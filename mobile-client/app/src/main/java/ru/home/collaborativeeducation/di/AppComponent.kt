package ru.home.collaborativeeducation.di

import dagger.Component
import ru.home.collaborativeeducation.ui.MainActivity
import ru.home.collaborativeeducation.ui.addNew.AddNewViewModel
import ru.home.collaborativeeducation.ui.course.CourseViewModel
import ru.home.collaborativeeducation.ui.course.details.CourseDetailsViewModel
import ru.home.collaborativeeducation.ui.main.MainViewModel

import javax.inject.Singleton

@Singleton
@Component(modules =[NetworkModule::class, Module::class])
interface AppComponent {
    fun inject(subj: MainActivity)
    fun inject(subj: MainViewModel)
    fun inject(subj: CourseViewModel)
    fun inject(subj: AddNewViewModel)
    fun inject(subj:
               CourseDetailsViewModel)
}
