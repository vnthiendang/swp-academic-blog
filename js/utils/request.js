const request = axios.create({
    baseURL: 'https://aidoctorbigsix-083a0cad02e1.herokuapp.com/blog/'
});

export const get = async (path, options = {})=>{
    const response = await request.get(path, options);
    return response.data;
};

export const post = async (path, data = {}) => {
    const response = await request.post(path, data);
    return response.data;
};

export const put = async (path, data = {}) => {
    const response = await request.put(path, data);
    return response.data;
};

export default request;