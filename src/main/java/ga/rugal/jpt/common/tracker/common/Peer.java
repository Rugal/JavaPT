/**
 * Copyright (C) 2011-2012 Turn, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ga.rugal.jpt.common.tracker.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * A basic BitTorrent peer.
 *
 * <p>
 * This class is meant to be a common base for the tracker and client, which would presumably
 * subclass it to extend its functionality and fields.
 * </p>
 *
 * @author mpetazzoni
 */
public class Peer
{

    private final InetSocketAddress address;

    private final String hostId;

    private ByteBuffer peerId;

    private String hexPeerId;

    /**
     * Instantiate a new peer.
     *
     * @param address The peer's address, with port.
     */
    public Peer(InetSocketAddress address)
    {
        this(address, null);
    }

    /**
     * Instantiate a new peer.
     *
     * @param ip   The peer's IP address.
     * @param port The peer's port.
     */
    public Peer(String ip, int port)
    {
        this(new InetSocketAddress(ip, port), null);
    }

    /**
     * Instantiate a new peer.
     *
     * @param ip         The peer's IP address.
     * @param port       The peer's port.
     * @param peerIdByte The byte-encoded peer ID.
     */
    public Peer(String ip, int port, ByteBuffer peerIdByte)
    {
        this(new InetSocketAddress(ip, port), peerIdByte);
    }

    /**
     * Instantiate a new peer.
     *
     * @param address    The peer's address, with port.
     * @param peerIdByte The byte-encoded peer ID.
     */
    public Peer(InetSocketAddress address, ByteBuffer peerIdByte)
    {
        this.address = address;
        this.hostId = String.format("%s:%d", this.address.getAddress(),
                                    this.address.getPort());

        this.setPeerId(peerIdByte);
    }

    /**
     * Tells whether this peer has a known peer ID yet or not.
     * <p>
     * @return
     */
    public boolean hasPeerId()
    {
        return this.peerId != null;
    }

    /**
     * Returns the raw peer ID as a {@link ByteBuffer}.
     * <p>
     * @return
     */
    public ByteBuffer getPeerIdByte()
    {
        return this.peerId;
    }

    /**
     * Set a peer ID for this peer (usually during handshake).
     *
     * @param peerId The new peer ID for this peer.
     */
    public void setPeerId(ByteBuffer peerId)
    {
        if (peerId != null)
        {
            this.peerId = peerId;
            this.hexPeerId = Torrent.byteArrayToHexString(peerId.array());
        } else
        {
            this.peerId = null;
            this.hexPeerId = null;
        }
    }

    public String getPeerId()
    {
        return new String(this.peerId.array());
    }

    /**
     * Get the hexadecimal-encoded string representation of this peer's ID.
     * <p>
     * @return
     */
    public String getHexPeerId()
    {
        return this.hexPeerId;
    }

    /**
     * Get the shortened hexadecimal-encoded peer ID.
     * <p>
     * @return
     */
    public String getShortHexPeerId()
    {
        return String.format("..%s",
                             this.hexPeerId.substring(this.hexPeerId.length() - 6).toUpperCase());
    }

    /**
     * Returns this peer's IP address.
     * <p>
     * @return
     */
    public String getIp()
    {
        return this.address.getAddress().getHostAddress();
    }

    /**
     * Returns this peer's InetAddress.
     * <p>
     * @return
     */
    public InetAddress getAddress()
    {
        return this.address.getAddress();
    }

    /**
     * Returns this peer's port number.
     * <p>
     * @return
     */
    public int getPort()
    {
        return this.address.getPort();
    }

    /**
     * Returns this peer's host identifier ("host:port").
     * <p>
     * @return
     */
    public String getHostIdentifier()
    {
        return this.hostId;
    }

    /**
     * Returns a binary representation of the peer's IP.
     *
     * @return
     */
    public byte[] getRawIp()
    {
        return this.address.getAddress().getAddress();
    }

    /**
     * Returns a human-readable representation of this peer.
     *
     * @return
     */
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder("peer://")
            .append(this.getIp()).append(":").append(this.getPort())
            .append("/");

        if (this.hasPeerId())
        {
            s.append(this.hexPeerId.substring(this.hexPeerId.length() - 6));
        } else
        {
            s.append("?");
        }

        if (this.getPort() < 10000)
        {
            s.append(" ");
        }

        return s.toString();
    }

    /**
     * Tells if two peers seem to look alike (i.e. they have the same IP, port and peer ID if they
     * have one).
     *
     * @param other
     *
     * @return
     */
    public boolean looksLike(Peer other)
    {
        if (other == null)
        {
            return false;
        }

        return this.hostId.equals(other.hostId)
               && (this.hasPeerId()
                   ? this.hexPeerId.equals(other.hexPeerId)
                   : true);
    }
}
