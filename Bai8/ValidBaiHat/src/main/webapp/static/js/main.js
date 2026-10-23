document.addEventListener('DOMContentLoaded', () => {
    // 1. Toast auto-dismiss
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-10px)';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });

    // 2. Client-side Real-time Validation & Live Preview
    const songNameInput = document.getElementById('name');
    const artistInput = document.getElementById('artist');
    const genreInput = document.getElementById('genre');

    const nameCount = document.getElementById('name-count');
    const artistCount = document.getElementById('artist-count');
    const genreCount = document.getElementById('genre-count');

    const previewTitle = document.getElementById('preview-title');
    const previewArtist = document.getElementById('preview-artist');
    const previewGenre = document.getElementById('preview-genre');

    // Regex patterns (Unicode letters, numbers, spaces; genre allows commas)
    // Matches Unicode letters via \p{L}, numbers via \p{N}, spaces via \s
    const nameArtistRegex = /^[\p{L}\p{N}\s]+$/u;
    const genreRegex = /^[\p{L}\p{N}\s,]+$/u;

    function validateField(input, counterEl, maxLen, regex, fieldType) {
        if (!input) return;
        const val = input.value.trim();
        const currentLen = input.value.length;

        // Update counter
        if (counterEl) {
            counterEl.textContent = `${currentLen} / ${maxLen}`;
            if (currentLen > maxLen) {
                counterEl.classList.add('limit-exceeded');
                counterEl.classList.remove('limit-near');
            } else if (currentLen > maxLen * 0.85) {
                counterEl.classList.add('limit-near');
                counterEl.classList.remove('limit-exceeded');
            } else {
                counterEl.classList.remove('limit-near', 'limit-exceeded');
            }
        }

        // Check validation
        let clientErrorEl = document.getElementById(input.id + '-client-error');
        if (!clientErrorEl) {
            clientErrorEl = document.createElement('div');
            clientErrorEl.id = input.id + '-client-error';
            clientErrorEl.className = 'error-feedback';
            input.parentElement.appendChild(clientErrorEl);
        }

        let errorMessage = '';

        if (input.value.length === 0) {
            errorMessage = `${fieldType} không được để trống`;
        } else if (currentLen > maxLen) {
            errorMessage = `${fieldType} không được vượt quá ${maxLen} ký tự`;
        } else if (!regex.test(input.value)) {
            if (input.id === 'genre') {
                errorMessage = `${fieldType} không được chứa các ký tự đặc biệt ngoại trừ dấu phẩy (",")`;
            } else {
                errorMessage = `${fieldType} không được chứa các ký tự đặc biệt (@, ;, ,, ., =, -, +, ...)`;
            }
        }

        if (errorMessage) {
            clientErrorEl.innerHTML = `<svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="8" x2="12" y2="12"></line><line x1="12" y1="16" x2="12.01" y2="16"></line></svg> ${errorMessage}`;
            clientErrorEl.style.display = 'flex';
            input.classList.add('is-invalid');
            input.classList.remove('is-valid');
        } else {
            clientErrorEl.style.display = 'none';
            input.classList.remove('is-invalid');
            input.classList.add('is-valid');
        }
    }

    if (songNameInput) {
        const updateName = () => {
            validateField(songNameInput, nameCount, 800, nameArtistRegex, 'Tên bài hát');
            if (previewTitle) {
                previewTitle.textContent = songNameInput.value.trim() || 'Tên bài hát hiển thị ở đây';
            }
        };
        songNameInput.addEventListener('input', updateName);
        songNameInput.addEventListener('blur', updateName);
        if (songNameInput.value) updateName();
    }

    if (artistInput) {
        const updateArtist = () => {
            validateField(artistInput, artistCount, 300, nameArtistRegex, 'Nghệ sĩ thể hiện');
            if (previewArtist) {
                previewArtist.textContent = artistInput.value.trim() ? `Ca sĩ: ${artistInput.value.trim()}` : 'Nghệ sĩ thể hiện';
            }
        };
        artistInput.addEventListener('input', updateArtist);
        artistInput.addEventListener('blur', updateArtist);
        if (artistInput.value) updateArtist();
    }

    if (genreInput) {
        const updateGenre = () => {
            validateField(genreInput, genreCount, 1000, genreRegex, 'Thể loại nhạc');
            if (previewGenre) {
                previewGenre.innerHTML = '';
                const parts = genreInput.value.split(',').map(s => s.trim()).filter(Boolean);
                if (parts.length > 0) {
                    parts.forEach(p => {
                        const tag = document.createElement('span');
                        tag.className = 'genre-tag';
                        tag.textContent = p;
                        previewGenre.appendChild(tag);
                    });
                } else {
                    previewGenre.innerHTML = '<span class="genre-tag">Chưa có thể loại</span>';
                }
            }
        };
        genreInput.addEventListener('input', updateGenre);
        genreInput.addEventListener('blur', updateGenre);
        if (genreInput.value) updateGenre();
    }

    // 3. Quick Genre Tags helper
    const quickTagBtns = document.querySelectorAll('.quick-tag-btn');
    quickTagBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            if (!genreInput) return;
            const tagValue = btn.dataset.tag;
            const currentVal = genreInput.value.trim();
            if (!currentVal) {
                genreInput.value = tagValue;
            } else {
                const tags = currentVal.split(',').map(t => t.trim());
                if (!tags.includes(tagValue)) {
                    genreInput.value = `${currentVal}, ${tagValue}`;
                }
            }
            genreInput.dispatchEvent(new Event('input'));
        });
    });

    // 4. Live Search in Song List
    const searchInput = document.getElementById('searchSongInput');
    const songRows = document.querySelectorAll('.song-row');
    const noResultsRow = document.getElementById('noResultsRow');

    if (searchInput && songRows.length > 0) {
        searchInput.addEventListener('input', () => {
            const query = searchInput.value.toLowerCase().trim();
            let visibleCount = 0;

            songRows.forEach(row => {
                const title = row.querySelector('.song-name-text')?.textContent.toLowerCase() || '';
                const artist = row.querySelector('.artist-badge')?.textContent.toLowerCase() || '';
                const genre = row.querySelector('.genre-tags')?.textContent.toLowerCase() || '';

                if (title.includes(query) || artist.includes(query) || genre.includes(query)) {
                    row.style.display = '';
                    visibleCount++;
                } else {
                    row.style.display = 'none';
                }
            });

            if (noResultsRow) {
                noResultsRow.style.display = visibleCount === 0 ? '' : 'none';
            }
        });
    }

    // 5. Delete Confirmation Modal
    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');
    const deleteSongNameSpan = document.getElementById('deleteSongName');
    const cancelDeleteBtn = document.getElementById('cancelDeleteBtn');

    window.confirmDelete = function(songId, songName) {
        if (!deleteModal || !deleteForm) return;
        deleteForm.action = `/songs/${songId}/delete`;
        if (deleteSongNameSpan) deleteSongNameSpan.textContent = `"${songName}"`;
        deleteModal.classList.add('active');
    };

    if (cancelDeleteBtn && deleteModal) {
        cancelDeleteBtn.addEventListener('click', () => {
            deleteModal.classList.remove('active');
        });

        deleteModal.addEventListener('click', (e) => {
            if (e.target === deleteModal) {
                deleteModal.classList.remove('active');
            }
        });
    }
});
