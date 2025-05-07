function fetchData(filterBloodGroup = "all") {
    const tableBody = document.querySelector("#userTable tbody");
    tableBody.innerHTML = "";

    fetch("DisplayDonar")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            const filteredData = filterBloodGroup === "all"
                ? data
                : data.filter((user) => user.blood_group === filterBloodGroup);
            filteredData.forEach((user) => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.blood_group}</td>
                    <td>${user.city}</td>
                    <td>${user.contact_details}</td>
                    <td>${user.email}</td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}

document.querySelector("#bloodGroupFilter").addEventListener("change", (event) => {
    const selectedBloodGroup = event.target.value;
    fetchData(selectedBloodGroup);
});

document.addEventListener("DOMContentLoaded", () => fetchData());
