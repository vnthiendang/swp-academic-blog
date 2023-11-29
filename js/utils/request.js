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

export const put = async (path, data = {}) => {
    const response = await request.put(path, data);
    return response.data;
};

export const del = async (path, options = {}) => {
    const response = await request.delete(path, options);
    return response.data;
};

export const postAward = async (path, data = {}) => {
        const response = await request.post(path, data);
        return response;
};

export default request;