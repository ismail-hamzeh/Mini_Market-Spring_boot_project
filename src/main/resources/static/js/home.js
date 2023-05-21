document.addEventListener("DOMContentLoaded", function () {
    var incrementButtons = document.querySelectorAll('.increment');
    var decrementButtons = document.querySelectorAll('.decrement');
    var counterInput = document.getElementById('totalQuantityInput');
    var counterOutput = document.getElementById('totalQuantityOutput');
    var priceInput = document.getElementById('totalPriceInput');
    var priceOutput = document.getElementById('totalPriceOutput');

    var footerDiv = document.querySelector('.footer');

    var totalPrice = 0.0; // Variable to store the total price

    incrementButtons.forEach(function (button) {
        button.addEventListener('click', function (e) {
            incrementCounter(e.target);
        });
    });

    decrementButtons.forEach(function (button) {
        button.addEventListener('click', function (e) {
            decrementCounter(e.target);
        });
    });

    function incrementCounter(button) {
        var currentItem = button.closest('.item');
        var counter = currentItem.querySelector('.item-quantity');
        var itemPriceElement = currentItem.querySelector('.item-price');
        var itemPrice = parseFloat(itemPriceElement.textContent.replace('JOD ',''));


        var currentValue = parseInt(counterInput.value);
        counterInput.value = currentValue + 1;
        counter.textContent++;
        counterOutput.textContent++;

        totalPrice += itemPrice;
        priceOutput.textContent = totalPrice.toFixed(2);
        priceInput.value = totalPrice.toFixed(2);

        footerDiv.style.display = "inline";

    }

    function decrementCounter(button) {
        var currentItem = button.closest('.item');
        var counter = currentItem.querySelector('.item-quantity');
        var itemPriceElement = currentItem.querySelector('.item-price');
        var itemPrice = parseFloat(itemPriceElement.textContent.replace('JOD ',''));

        var currentValue = parseInt(counterInput.value);
        if (parseInt(counter.textContent) > 0) {
            counterInput.value = currentValue - 1;
            counter.textContent--;
            counterOutput.textContent--;

            totalPrice -= itemPrice;
            priceOutput.textContent = totalPrice.toFixed(2);
            priceInput.value = totalPrice.toFixed(2);
        }

        if (parseInt(counterOutput.textContent) === 0){
            footerDiv.style.display = "none";
        }
    }
});

function filterFunction(filter) {
    const items = document.querySelectorAll('.item');

    items.forEach((item) => {
        if (filter === 'all') {
            item.style.display = 'block';
        } else if (item.classList.contains(filter)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
}
