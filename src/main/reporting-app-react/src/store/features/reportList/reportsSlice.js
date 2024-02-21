import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    reports: [],
    loading: false,
    activePage: 1,
    pageSize: 7,
    totalPages: 1,
    ascendingOrder: true,
    isSearched: false,
};

export const reportsSlice = createSlice({
    name: "reports",
    initialState,
    reducers: {
        setReports: (state, action) => {
            state.reports = action.payload
        },
        setLoading: (state, action) => {
            state.loading = action.payload
        },
        setActivePage: (state, action) => {
            state.activePage = action.payload
        },
        setPageSize: (state, action) => {
            state.pageSize = action.payload
        },
        setAscendingOrder: (state, action) => {
            state.ascendingOrder = action.payload
        },
        setIsSearched: (state, action) => {
            state.isSearched = action.payload
        },
        setTotalPages: (state, action) =>{
            state.totalPages = action.payload
        }
    }
})

export const { setReports, setLoading, setActivePage, setPageSize, setAscendingOrder, setIsSearched, setTotalPages } = reportsSlice.actions

export default reportsSlice.reducer
