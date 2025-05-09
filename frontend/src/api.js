export const getAllEmployees = () => {
  return fetch('/api/employee/all').then((response) => {
    return response.json()
  })
}

export const getAllSeats = () => {
  return fetch('/api/seat/all').then((response) => {
    return response.json()
  })
}

export const updateEmployeeSeat = (empId, floorSeatSeq) => {
  return fetch(`/api/employee/${empId}/seat`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: floorSeatSeq,
  }).then((response) => {
    return response.json()
  })
}
