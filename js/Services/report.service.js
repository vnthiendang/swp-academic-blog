import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

export const getAllReports = async () => {
    try {
      const response = await request.get('report/GetAll', {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching reports:', error);
    }
};

//GET REPORT DETAIL
export const getReportById = async (reportId) => {
    try {
      const response = await request.get(`report/${reportId}`, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching report by id:', error);
    }
};

export const createReport = async (model) => {
    try {
      const response = await request.post(`report/post`, model,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response;
    } catch (error) {
      console.error('Error creating report:', error);
    } 
};

export const editReport = async (reportId, model) => {
    try {
      const response = await request.put(`report/${reportId}`, model, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response;
      
    } catch (error) {
      console.error('Error edit report:', error);
    }
  };