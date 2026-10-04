import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import { useAuth } from "../context/AuthContext";

function EmployeeList() {
  const [employees, setEmployees] = useState([]);

  const [editingId, setEditingId] = useState(null);
  const [editingName, setEditingName] = useState("");
  const [editingEmail, setEditingEmail] = useState("");
  const [editingRole, setEditingRole] = useState("");

  const navigate = useNavigate();
  const { logout } = useAuth();

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await axiosInstance.get("/api/employees");

        setEmployees(response.data);
      } catch (error) {
        console.log("Failed to fetch employees");
        console.log(error);
      }
    };

    fetchEmployees();
  }, []);

  const handleEdit = (employee) => {
    setEditingId(employee.id);
    setEditingName(employee.name);
    setEditingEmail(employee.email);
    setEditingRole(employee.role);
  };

  const handleUpdate = async (id) => {
    try {
      const response = await axiosInstance.put(
        `/api/employees/${id}`,
        {
          name: editingName,
          email: editingEmail,
          role: editingRole
        }
      );

      setEmployees(
        employees.map((employee) =>
          employee.id === id ? response.data : employee
        )
      );

      setEditingId(null);
      setEditingName("");
      setEditingEmail("");
      setEditingRole("");

      console.log("Employee updated successfully");
    } catch (error) {
      console.log("Failed to update employee");
      console.log(error);
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this employee?"
    );

    if (!confirmed) {
      return;
    }

    try {
      await axiosInstance.delete(`/api/employees/${id}`);

      setEmployees(
        employees.filter((employee) => employee.id !== id)
      );

      console.log("Employee deleted successfully");
    } catch (error) {
      console.log("Failed to delete employee");
      console.log(error);
    }
  };

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="container">

      <h1>All Employees</h1>

      <button onClick={() => navigate("/")} style={{ marginRight: "10px" }}>
        Back to Home
      </button>

      <button onClick={handleLogout}>
        Logout
      </button>

      <div className="employee-table-container">

        <table className="employee-table">

          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {employees.map((emp) => (
              <tr key={emp.id}>

                <td>{emp.id}</td>

                <td>
                  {editingId === emp.id ? (
                    <input
                      value={editingName}
                      onChange={(e) => setEditingName(e.target.value)}
                    />
                  ) : (
                    emp.name
                  )}
                </td>

                <td>
                  {editingId === emp.id ? (
                    <input
                      type="email"
                      value={editingEmail}
                      onChange={(e) => setEditingEmail(e.target.value)}
                    />
                  ) : (
                    emp.email
                  )}
                </td>

                <td>
                  {editingId === emp.id ? (
                    <select
                      value={editingRole}
                      onChange={(e) => setEditingRole(e.target.value)}
                    >
                      <option value="EMPLOYEE">EMPLOYEE</option>
                      <option value="ADMIN">ADMIN</option>
                    </select>
                  ) : (
                    emp.role
                  )}
                </td>

                <td>
                  {editingId === emp.id ? (
                    <>
                      <button onClick={() => handleUpdate(emp.id)}>
                        Save
                      </button>

                      <button onClick={() => setEditingId(null)}>
                        Cancel
                      </button>
                    </>
                  ) : (
                    <>
                      <button onClick={() => handleEdit(emp)} style={{marginRight:"10px"}}>
                        Edit
                      </button>

                      <button onClick={() => handleDelete(emp.id)}>
                        Delete
                      </button>
                    </>
                  )}
                </td>

              </tr>
            ))}
          </tbody>

        </table>

      </div>

    </div>
  );
}

export default EmployeeList;