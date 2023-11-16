import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

//GET ALL REPORTS
export const getAllReportViolation = async () => {
    try {
      const response = await request.get('reportViolation/GetAll', {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching report violations:', error);
    }
};

//GET REPORT DETAIL
export const getReportViolationById = async (id) => {
    try {
      const response = await request.get(`reportViolation/${id}`, {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching report violation by id:', error);
    }
};

export const createReportViolation = async (model) => {
    try {
      const response = await request.post(`reportViolation/post`, model,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response;
    } catch (error) {
      console.error('Error creating report violation:', error);
    } 
};