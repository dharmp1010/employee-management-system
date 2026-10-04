import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import { useAuth } from "../context/AuthContext";

function MyLeaves() {

    const [leaves, setLeaves] = useState([]);
    const [reason, setReason] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const navigate = useNavigate();
    const { logout } = useAuth();

    const fetchLeaves = async () => {
        try {
        const response = await axiosInstance.get("/api/leaves/my");
        setLeaves(response.data);
        } catch (error) {
        console.log("Failed to fetch leaves");
        console.log(error);
        }
    };

    useEffect(() => {
        fetchLeaves();
}, []);

    const handleSubmit = async (e) => {
    e.preventDefault();

    if (!reason || !startDate || !endDate) {
    alert("Please fill all fields");
    return;
    }

    if (endDate < startDate) {
    alert("End date cannot be before start date");
    return;
    }

    try {
    await axiosInstance.post("/api/leaves", {
        reason: reason,
        startDate: startDate,
        endDate: endDate
    });

    console.log("Leave applied successfully");
    setReason("");
    setStartDate("");
    setEndDate("");
    fetchLeaves();
    } 
    catch (error) {
    console.log("Failed to apply for leave");
    console.log(error);
    }
    };
    const handleLogout = () => {
    logout();
    navigate("/login");
    };

    return (
        <div className="container">
        <h1>My Leaves</h1>
        <button onClick={() => navigate("/")}>
            Back to Home
        </button>
        <br/>
        <br/>
        <button onClick={handleLogout}>
            Logout
        </button>
        <br/>
        <br/>
        <h2>Apply for Leave</h2>
        <form onSubmit={handleSubmit}>
            <div>
            <label>Reason</label>
            <br/>
            <input
                type="text"
                placeholder="Enter reason"
                value={reason}
                onChange={(e) => setReason(e.target.value)}
            />
            </div>
            <br/>
            <div>
            <label>Start Date</label>
            <br/>
            <input
                type="date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
            />
            </div>
            <br />
            <div>
            <label>End Date</label>
            <br />

            <input
                type="date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
            />
            </div>

            <br />

            <button type="submit">
            Apply for Leave
            </button>
        </form>
        <br />
        <h2>My Leave Requests</h2>
        <button onClick={fetchLeaves}>
            Refresh Leaves
        </button>
    <br />
    <br />
        <table border="1" cellPadding="8" className="leave-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Reason</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            {leaves.map((leave) => (
                <tr key={leave.id}>
                <td>{leave.id}</td>
                <td>{leave.reason}</td>
                <td>{leave.startDate}</td>
                <td>{leave.endDate}</td>
                <td>{leave.status}</td>
                </tr>
            ))}

            </tbody>

        </table>

        </div>
    );
    }
export default MyLeaves;