package com.amos_tech_code.travelapp.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)

}