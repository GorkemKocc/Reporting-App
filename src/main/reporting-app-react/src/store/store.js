import { configureStore } from '@reduxjs/toolkit'
import  reportList  from './features/reportList/reportsSlice'

export const store = configureStore({
    reducer: {
        reports : reportList
    }
  })
