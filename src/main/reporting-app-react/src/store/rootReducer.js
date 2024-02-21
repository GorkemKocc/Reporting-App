import { combineReducers } from 'redux';
import reportsReducer from './reportsReducer';

const rootReducer = combineReducers({
    reports: reportsReducer,
});

export default rootReducer;
