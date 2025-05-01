// JavaScript to calculate total price for reservation
function calculateTotal() {
    // Get the start and end dates. If empty, return undefined (?)
    const start= document.getElementById("start")?.value;
    const end = document.getElementById("end")?.value;
    const pricePerDay = parseFloat(document.getElementById("pricePerDay")?.value);

    // Check if start and end dates are valid
    if (start && end) {
        // Convert to Date objects
        const startDate = new Date(start);
        const endDate = new Date(end);

        const timeDiff = endDate.getTime() - startDate.getTime();
        const dayDiff = timeDiff / (1000 * 3600 * 24); // Convert milliseconds to days

        if (dayDiff > 0) {
            // Calculate total price
            const totalPrice = dayDiff * pricePerDay;

            // Update HTML elements with the calculated total price
            const formattedTotalPrice = totalPrice.toFixed(2);
            document.getElementById("displayTotalPrice").innerText = formattedTotalPrice;
            document.getElementById("totalPriceHidden").value = formattedTotalPrice;
        } else {
            alert("End date must be after start date.");
        }
    } else {
        // If either date is not selected, reset the total price
        document.getElementById("displayTotalPrice").innerText = "Please select both dates";
    }
}