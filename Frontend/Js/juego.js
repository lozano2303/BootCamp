document.addEventListener('DOMContentLoaded', async () => {
    await inicializarJuego();
});

async function inicializarJuego() {
    const playerBar = document.getElementById('player-bar');
    playerBar.innerHTML = '';

    const gameId = localStorage.getItem('gameId');
    const nombres = JSON.parse(localStorage.getItem('jugadores') || '[]');
    const playerIds = JSON.parse(localStorage.getItem('playerIds') || '[]');

    console.log("🔁 Iniciando juego con ID:", gameId);
    console.log("👥 Jugadores:", nombres);
    console.log("🆔 IDs:", playerIds);

    if (!gameId || playerIds.length === 0) {
        mostrarMensajeError('Sin partida activa o jugadores.');
        return;
    }

    try {
        // 1) Asignar jugadores al juego (si no lo estaban)
        const asignacion = await asignarJugadoresASala(gameId, playerIds);
        console.log('✅ Jugadores asignados:', asignacion.data);

        // 2) Obtener todos los jugadores del juego (incluso si ya estaban)
        const jugadores = await obtenerJugadores(gameId);
        console.log('🎯 Jugadores en juego:', jugadores);

        if (!jugadores || jugadores.length === 0) {
            mostrarMensajeError('No hay jugadores asignados.');
            return;
        }

        // 3) Mostrar visualmente
        mostrarJugadores(jugadores);

    } catch (error) {
        console.error('❌ Error al iniciar el juego:', error);
        mostrarMensajeError('Error al cargar jugadores.');
    }
}

function mostrarMensajeError(mensaje) {
    const playerBar = document.getElementById('player-bar');
    playerBar.innerHTML = `<div class="player-slot">${mensaje}</div>`;
}

async function obtenerJugadores(gameId) {
    const response = await fetch(`http://localhost:8080/playergames/list/${gameId}`);

    if (!response.ok) {
        throw new Error(`No se pudo obtener jugadores: ${response.statusText}`);
    }

    return await response.json(); // [{ id, playerName }, …]
}

function mostrarJugadores(jugadores) {
    const playerBar = document.getElementById('player-bar');

    const titulo = document.createElement('h3');
    titulo.textContent = `🎮 Jugadores en esta partida: ${jugadores.length}`;
    playerBar.appendChild(titulo);

    jugadores.forEach(player => {
        const slot = document.createElement('div');
        slot.className = 'player-slot';

        const nombre = document.createElement('span');
        nombre.className = 'player-name';
        nombre.textContent = player.playerName;

        const cardContainer = document.createElement('div');
        cardContainer.className = 'card-container';

        for (let i = 0; i < 8; i++) {
            const card = document.createElement('div');
            card.className = 'card';
            cardContainer.appendChild(card);
        }

        slot.appendChild(nombre);
        slot.appendChild(cardContainer);
        playerBar.appendChild(slot);
    });
}

async function asignarJugadoresASala(gameId, playerIds) {
    const res = await fetch(`http://localhost:8080/playergames/assign/${gameId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(playerIds)
    });

    let dto;
    try {
        dto = await res.json();
    } catch {
        throw new Error(`Respuesta inválida al asignar jugadores a la sala ${gameId}`);
    }

    if (!res.ok) {
        const msg = dto?.message || `Error desconocido al asignar jugadores a la sala`;
        throw new Error(`HTTP ${res.status}: ${msg}`);
    }

    return dto;
}
