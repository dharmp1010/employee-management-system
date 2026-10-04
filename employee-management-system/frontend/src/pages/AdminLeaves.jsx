import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import { useAuth } from "../context/AuthContext";

function AdminLeaves() {

    const [leaves, setLeaves] = useState([]);
    const navigate = useNavigate();
    const { logout } = useAuth();
    const fetchLeaves = async () => {
        try {
        const response = await axiosInstance.get("/api/leaves");
        setLeaves(response.data);
        } catch (error) {
        console.log("Failed to fetch leaves");
        console.log(error);
        }
    };

    useEffect(() => {
        fetchLeaves();
    }, []);
    const handleLogout = () => {
    logout();
    navigate("/login");
    };

const handleApprove = async (id) => {
    try {
    await axiosInstance.put(`/api/leaves/${id}/approve`);
    console.log("Leave approved");
    fetchLeaves();
    } 
    catch (error) {
    console.log("Failed to approve leave");
    console.log(error);
    }
  };

const handleReject = async (id) => {
    try {
    await axiosInstance.put(`/api/leaves/${id}/reject`);
    console.log("Leave rejected");
    fetchLeaves();
    } 
    catch (error) {
    console.log("Failed to reject leave");
    console.log(error);
    }
  };

return (
    <div className="container">
        <h1>All Leave Requests</h1>
        <button onClick={() => navigate("/")} style={{ marginLeft: "10px" }}>
            Back to Home
        </button>
        <button onClick={handleLogout} style={{ marginLeft: "10px" }}>
            Logout
        </button>

<br />
<br />
        <table border="1" cellPadding="8">
        <thead>
          <tr>
            <th>ID</th>
            <th>Employee</th>
            <th>Reason</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {leaves.map((leave) => (
            <tr key={leave.id}>
                <td>{leave.id}</td>
                <td>{leave.employeeName}</td>
                <td>{leave.reason}</td>
                <td>{leave.startDate}</td>
                <td>{leave.endDate}</td>
                <td>{leave.status}</td>
            <td>
                {leave.status === "PENDING" && (
                  <>
                    <button
                      onClick={() => handleApprove(leave.id)}
                    >
                      Approve
                    </button>
                    {" "}
                    <button
                      onClick={() => handleReject(leave.id)}
                    >
                      Reject
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminLeaves;