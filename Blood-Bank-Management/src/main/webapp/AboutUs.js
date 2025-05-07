fetch('AboutUs')
            .then(response => response.text())
            .then(data => {
                document.getElementById('aboutUsContent').innerHTML = data;
            })
            .catch(error => {
                console.error('Error fetching About Us details:', error);
                document.getElementById('aboutUsContent').innerHTML = 
                    '<p>Unable to load content. Please try again later.</p>';
            });