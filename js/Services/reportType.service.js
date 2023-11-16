import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

//GET ALL REPORT TYPES
export const getAllReportType = async () => {
    try {
      const response = await request.get('reportType/GetAll', {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching report types:', error);
    }
};

export const getReportTypeById = async (id) => {
    try {
      const response = await request.get(`reportType/${id}`, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching report type by id:', error);
    }
};

export const createReportType = async (model) => {
    try {
      const response = await request.post(`reportType/post`, model,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response;
    } catch (error) {
      console.error('Error creating report type:', error);
    } 
};

export const editReportType = async (id, model) => {
    try {
      const response = await request.put(`reportType/${id}`, model, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response;
      
    } catch (error) {
      console.error('Error edit report type:', error);
    }
};