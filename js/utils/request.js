const request = axios.create({
    baseURL: 'http://localhost:8080/blog/'
});

export const get = async (path, options = {})=>{
    const response = await request.get(path, options);
    return response.data;
};

export const post = async (path, data = {}) => {
    const response = await request.post(path, data);
    return response.data;
};

export default request;